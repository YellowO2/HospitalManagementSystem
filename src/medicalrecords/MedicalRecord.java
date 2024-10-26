package SC2002_GP;
import java.util.List;
import java.util.ArrayList;

public class MedicalRecord {
	private String patientId;
	private List<String>diagnoses;
	private List<Prescription> prescriptions;
	 private List<String> treatmentPlans;
	
	//Constructor
	public MedicalRecord(String patientId) {
		this.patientId = patientId;
		this.diagnoses = new ArrayList<>();
		this.prescriptions = new ArrayList<>();
	}
	
	public void addDiagnosis(String diagnoses) {
		diagnoses.add(diagnoses);
	}
	
	public void addPrescription(Prescription prescription) {
		prescription.add(prescription);
	}
	
	public void addTreatmentPlan(String treatmentPlan) {
        treatmentPlans.add(treatmentPlan);
    }
	
	public void viewMedicalRecord() {
		System.out.println("Patient ID: " + patientId);
        System.out.println("Diagnoses: " + diagnoses);
        System.out.println("Prescriptions: " + prescriptions);
	}
	
}
