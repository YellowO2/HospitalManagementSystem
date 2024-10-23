# Hospital Management System

## Overview
The Hospital Management System (HMS) is a software application designed to manage various aspects of hospital operations as project for NTU SC2002.

## Getting Started

### Compiling and Running the Application
To compile the Java source files, run the following command:

   `javac -d ../out *.java authentication/*.java users/*.java`
   
   
   (Rmb to include more folders as we build more stuff)
   
After compilation, you can run the Hospital Management System with:
   
   `java -cp ../out HospitalManagementSystem`

## Features
### Patient Checklist
- [x] **Information Access:**
  - [x] View own medical record (move this to individual checklist later)
    - [x] Patient ID
    - [x] Name
    - [ ] Date of Birth
    - [ ] Gender
    - [ ] Contact Information (e.g., phone number, email address)
    - [ ] Blood Type
    - [ ] Past Diagnoses and Treatments
  - [ ] Update non-medical personal information (email address, contact number)
  - [ ] Cannot modify past diagnoses, prescribed medications, treatments, or blood type
- [ ] **Appointment Management:**
  - [ ] View available appointment slots with doctors
  - [ ] Schedule Appointments
    - [ ] Choose a doctor
    - [ ] Select a date
    - [ ] Select an available time slot
  - [ ] Reschedule Appointments
    - [ ] Change an existing appointment to a new slot
  - [ ] Cancel Appointments
  - [ ] View the status of scheduled appointments
    - [ ] Status updated based on doctor’s response (confirmed, canceled, completed)
  - [ ] View Appointment Outcome Records of past appointments

## Contributing
