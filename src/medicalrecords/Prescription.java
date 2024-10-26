package SC2002_GP;

public class Prescription {
	private String medicineName;
	private String dosage;
	private String frequency;
	
	public Prescription(String medicineName, String dosage, String frequency) {
		this.medicineName = medicineName;
		this.dosage = dosage;
		this.frequency = frequency;
	}
	
	public String getDescriptionDetails() {
		return medicineName + "," + dosage + " - " + frequency;
	}
}
