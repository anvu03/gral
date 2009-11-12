package openjchart.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents a view on a DataSource.
 * This is achieved by simply mapping strings to column indexes.
 * @see DataSource
 */
public class DataSeries implements DataSource {
	private DataSource data;
	private final List<Integer> cols;
	private String name;

	private class DataSeriesIterator implements Iterator<Number[]> {
		private int row;

		@Override
		public boolean hasNext() {
			return row < getRowCount();
		}

		@Override
		public Number[] next() {
			return get(row++);
		}

		@Override
		public void remove() {
		}
	}

	/**
	 * Constructor without name.
	 * @param data Data source
	 * @param cols Column numbers
	 */
	public DataSeries(DataSource data, int... cols) {
		this(null, data, cols);
	}
	
	/**
	 * Constructor.
	 * @param name Descriptive name
	 * @param data Data source
	 * @param cols Column numbers
	 */
	public DataSeries(String name, DataSource data, int... cols) {
		this.name = name;
		this.data = data;
		this.cols = new ArrayList<Integer>(cols.length);
		for (int col : cols) {
			this.cols.add(col);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addDataListener(DataListener dataListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Number[] get(int row) {
		Number[] rowOrig = data.get(row);
		Number[] rowFiltered = new Number[cols.size()];
		int filteredIndex = 0;
		for (int index : cols) {
			rowFiltered[filteredIndex++] = rowOrig[index];
		}
		return rowFiltered;
	}

	@Override
	public Number get(int col, int row) {
		int dataCol = -1;
		try {
			dataCol = cols.get(col);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return data.get(dataCol, row);
	}

	@Override
	public int getColumnCount() {
		return cols.size();
	}

	@Override
	public Number getMax(int col) {
		int dataCol = -1;
		try {
			dataCol = cols.get(col);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return data.getMax(dataCol);
	}

	@Override
	public Number getMin(int col) {
		int dataCol = -1;
		try {
			dataCol = cols.get(col);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return data.getMin(dataCol);
	}

	@Override
	public int getRowCount() {
		return data.getRowCount();
	}

	@Override
	public void removeDataListener(DataListener dataListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Number[]> iterator() {
		return new DataSeriesIterator();
	}
}
