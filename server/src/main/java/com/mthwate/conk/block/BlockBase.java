package com.mthwate.conk.block;

/**
 * @author mthwate
 */
public abstract class BlockBase implements Block {

	protected Data data = new Data();

	@Override
	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public Data getData() {
		return data;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public String[] getTextures() {
		return new String[] {getName()};
	}

}
