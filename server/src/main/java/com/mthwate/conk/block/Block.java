package com.mthwate.conk.block;

/**
 * @author mthwate
 */
public interface Block {

	String getName();

	void setData(Data data);

	Data getData();

	boolean isSolid();

}
