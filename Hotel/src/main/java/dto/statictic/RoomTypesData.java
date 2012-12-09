package dto.statictic;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RoomTypesData {

	private String roomTypeName;

	private int numberOccupiedRooms;

	private float summaryGain;

    public RoomTypesData() {
    }

    public RoomTypesData(String roomTypeName, int numberOccupiedRooms, float summaryGain) {
        this.roomTypeName = roomTypeName;
        this.numberOccupiedRooms = numberOccupiedRooms;
        this.summaryGain = summaryGain;
    }

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public int getNumberOccupiedRooms() {
		return numberOccupiedRooms;
	}

	public float getSummaryGain() {
		return summaryGain;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,	ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
