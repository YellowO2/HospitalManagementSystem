# Hospital Management System

## Overview
The Hospital Management System (HMS) is a software application designed to manage various aspects of hospital operations as project for NTU SC2002.

## Getting Started

### Compiling and Running the Application
To compile the Java source files, run the following command:

```
javac -d out src/**/*.java
java -cp out HospitalManagementSystem
```

## **Packages Overview**

### **User Package**

#### **User Checklist** (Inherited by all roles)

**Properties:**
- [ ] `id`: Unique hospital ID
- [ ] `name`: Full name
- [ ] `password`: Password for login (default: "password")
- [ ] `role`: User role (Patient, Doctor, Pharmacist, Administrator)
- [ ] `phoneNumber`: Contact number
- [ ] `emailAddress`: Email address
- [ ] `bloodType`: Blood type
- [ ] `dateOfBirth`: Date of birth
- [ ] `gender`: Gender

**Methods:**
- [ ] `changePassword()`: Allows users to change their password
- [ ] `viewProfile()`: Abstract method to be implemented by subclasses

---

#### **Patient Checklist** (Inherits from User)

**Properties:**
- [ ] `medicalRecord`: Patient's medical record
- [ ] `appointments`: List of patient's appointments

**Methods:**
- [ ] `viewMedicalRecord()`: View the patient's own medical record
- [ ] `updateContactInfo()`: Update non-medical personal information (e.g., email, phone)
- [ ] `viewAppointments()`: View scheduled appointments
- [ ] `scheduleAppointment()`: Schedule a new appointment
- [ ] `rescheduleAppointment()`: Reschedule an existing appointment
- [ ] `cancelAppointment()`: Cancel an appointment
- [ ] `viewAppointmentOutcome()`: View the outcome of past appointments

---

### **Medical Record Package**

#### **Medical Record Checklist**

**Properties:**
- [ ] `patientId`: Patient's unique ID
- [ ] `name`: Patient's full name
- [ ] `dateOfBirth`: Patient's date of birth
- [ ] `gender`: Patient's gender
- [ ] `phoneNumber`: Patient's phone number
- [ ] `emailAddress`: Patient's email address
- [ ] `bloodType`: Patient's blood type
- [ ] `diagnoses`: List of past diagnoses
- [ ] `treatments`: List of treatments or procedures
- [ ] `prescriptions`: List of prescribed medications

**Methods:**
- [ ] `viewMedicalRecord()`: View the full medical record
- [ ] `addDiagnosis()`: Add a diagnosis (Doctor action)
- [ ] `addTreatment()`: Add a treatment (Doctor action)
- [ ] `addPrescription()`: Add a prescription (Doctor action)

---

#### **Prescription Checklist**

**Properties:**
- [ ] `medicationName`: Name of the medication
- [ ] `dosage`: Dosage information
- [ ] `frequency`: Frequency of administration (e.g., "2 times per day")
- [ ] `status`: Status of the prescription (e.g., pending, prescribed, filled)
- [ ] `amount`: Amount of medication prescribed
- [ ] `instructions`: Special instructions for taking the medication

**Methods:**
- [ ] `viewPrescriptionDetails()`: View the details of the prescription
- [ ] `updatePrescriptionStatus()`: Update the status of the prescription (Pharmacist action)

---

#### **Diagnosis Checklist**

**Properties:**
- [ ] `diagnosisName`: Name of the diagnosis (e.g., "Diabetes")
- [ ] `severity`: Severity of the diagnosis (e.g., "Mild", "Severe")
- [ ] `diagnosisDate`: The date when the diagnosis was made
- [ ] `doctorName`: Name of the doctor who made the diagnosis

**Methods:**
- [ ] `getDiagnosisName()`: Returns the name of the diagnosis
- [ ] `getSeverity()`: Returns the severity level of the diagnosis
- [ ] `getDiagnosisDate()`: Returns the date of the diagnosis
- [ ] `getDoctorName()`: Returns the name of the doctor who made the diagnosis
- [ ] `viewDiagnosis()`: Displays the details of the diagnosis (name, severity, date, doctor)

---

#### **Treatment Checklist**

**Properties:**
- [ ] `treatmentName`: Name of the treatment (e.g., "Physical Therapy")
- [ ] `treatmentDate`: The date when the treatment was administered
- [ ] `doctorName`: Name of the doctor who administered or prescribed the treatment
- [ ] `treatmentDetails`: Additional details about the treatment (e.g., duration, procedure)

**Methods:**
- [ ] `getTreatmentName()`: Returns the name of the treatment
- [ ] `getTreatmentDate()`: Returns the date of the treatment
- [ ] `getDoctorName()`: Returns the name of the doctor who administered or prescribed the treatment
- [ ] `getTreatmentDetails()`: Returns any additional details about the treatment
- [ ] `viewTreatment()`: Displays the details of the treatment (name, date, doctor, and details)

---

### **Appointment Package**

#### **Appointment Checklist**

**Properties:**
- [ ] `appointmentId`: Unique appointment ID
- [ ] `patientId`: Linked patient ID
- [ ] `doctorId`: Linked doctor ID
- [ ] `appointmentDate`: Date and time of the appointment
- [ ] `status`: Appointment status (e.g., confirmed, canceled, completed)

**Appointment Outcome Properties:**
- [ ] `serviceProvided`: Type of service provided (e.g., consultation, X-ray)
- [ ] `consultationNotes`: Doctor's notes for the appointment
- [ ] `prescriptions`: List of prescribed medications during the appointment

**Methods:**
- [ ] `scheduleAppointment()`: Schedule a new appointment
- [ ] `rescheduleAppointment()`: Reschedule an existing appointment
- [ ] `cancelAppointment()`: Cancel the appointment
- [ ] `viewAppointmentDetails()`: View appointment details
- [ ] `setAppointmentOutcome()`: Set the outcome of the appointment (Doctor action)

---
