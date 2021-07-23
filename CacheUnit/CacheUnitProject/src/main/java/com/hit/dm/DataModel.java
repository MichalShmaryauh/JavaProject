package com.hit.dm;

/**
 * Concrete class represents an entry of data in the disk.
 * 
 * @author ъош
 *
 * @param <T>
 */
public class DataModel<T> implements java.io.Serializable {

	private static final long serialVersionUID = -616017516154926601L;

	private Long dataModelId;
	private Object content;

	public DataModel(Long id, T content) {
		this.dataModelId = id;
		this.content = content;
	}

	public Long getDataModelId() {
		return dataModelId;
	}

	public void setDataModelId(Long dataModelId) {
		this.dataModelId = dataModelId;
	}

	@SuppressWarnings("unchecked")
	public T getContent() {
		return (T) content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dataModelId == null) ? 0 : dataModelId.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModel<T> other = (DataModel<T>) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dataModelId == null) {
			if (other.dataModelId != null)
				return false;
		} else if (!dataModelId.equals(other.dataModelId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataModel [dataModelId=" + dataModelId + ", content=" + content + "]";
	}

}
