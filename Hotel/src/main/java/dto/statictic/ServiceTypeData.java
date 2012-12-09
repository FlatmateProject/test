package dto.statictic;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ServiceTypeData {

	private String typeName;
	
	private long summaryTime;
	
	private float summaryGain;

    public ServiceTypeData() {
    }

    public ServiceTypeData(String typeName, long summaryTime, float summaryGain) {
        this.typeName = typeName;
        this.summaryTime = summaryTime;
        this.summaryGain = summaryGain;
    }

    public String getTypeName() {
		return typeName;
	}

	public long getSummaryTime() {
		return summaryTime;
	}

	public float getSummaryGain() {
		return summaryGain;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,	ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
