package domain;

public class RentRequest implements Comparable<RentRequest> {
	private int idRequest;
	private int startingMinutes;
	private int finishingMinutes;
	
	public RentRequest(int idRequest, int startingMinutes, int finishingMinutes) {
		this.idRequest = idRequest;
		this.startingMinutes = startingMinutes;
		this.finishingMinutes = finishingMinutes;
	}
	public int getStartingMinutes() {
		return startingMinutes;
	}
	public void setStartingMinutes(int startingMinutes) {
		this.startingMinutes = startingMinutes;
	}
	public int getFinishingMinutes() {
		return finishingMinutes;
	}
	public void setFinishingMinutes(int finishingMinutes) {
		this.finishingMinutes = finishingMinutes;
	}
	@Override
	public int compareTo(RentRequest other) {
		return ((Integer)finishingMinutes).compareTo((Integer)other.finishingMinutes);
		
	}
	public int getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}
	
}
