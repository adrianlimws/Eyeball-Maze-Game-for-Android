package nz.ac.ara.adrianlim.eyeballmaze.interfaces;

public interface IGoalHolder {
	void addGoal(int row, int column);

	int getGoalCount();

	boolean hasGoalAt(int targetRow, int targetColumn);

	int getCompletedGoalCount();
}
