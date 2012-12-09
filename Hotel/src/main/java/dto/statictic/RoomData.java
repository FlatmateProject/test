package dto.statictic;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RoomData {

	private long roomId;

	private int occupationNumber;

	private float summaryGain;

    public RoomData() {
    }

    public RoomData(long roomId, int occupationNumber, float summaryGain) {
        this.roomId = roomId;
        this.occupationNumber = occupationNumber;
        this.summaryGain = summaryGain;
    }

	public long getRoomId() {
		return roomId;
	}

	public int getOccupationNumber() {
		return occupationNumber;
	}

	public float getSummaryGain() {
		return summaryGain;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,	ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
