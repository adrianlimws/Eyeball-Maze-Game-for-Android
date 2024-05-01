package nz.ac.ara.adrianlim.eyeballmaze.interfaces;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;

public interface IMoving {
	public boolean canMoveTo(int destinationRow, int destinationColumn);

	public Message MessageIfMovingTo(int destinationRow, int destinationColumn);

	public boolean isDirectionOK(int destinationRow, int destinationColumn);

	public Message checkDirectionMessage(int destinationRow, int destinationColumn);

	public boolean hasBlankFreePathTo(int destinationRow, int destinationColumn);

	public Message checkMessageForBlankOnPathTo(int destinationRow, int destinationColumn);

	public void moveTo(int destinationRow, int destinationColumn);
}
