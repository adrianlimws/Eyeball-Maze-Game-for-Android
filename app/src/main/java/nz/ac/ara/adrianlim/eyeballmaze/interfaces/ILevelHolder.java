package nz.ac.ara.adrianlim.eyeballmaze.interfaces;

public interface ILevelHolder {
	void addLevel(int height, int width);

	int getLevelWidth();

	int getLevelHeight();

	void setLevel(int levelNumber);

	int getLevelCount();
}
