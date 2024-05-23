package nz.ac.ara.adrianlim.eyeballmaze.interfaces;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;

public interface IEyeballHolder {
	void addEyeball(int row, int column, Direction direction);

	int getEyeballRow();

	int getEyeballColumn();

	Direction getEyeballDirection();
}
