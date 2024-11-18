# Hospital Management System (HMS)

### Overview
The **Hospital Management System (HMS)** is a software application developed to manage various aspects of hospital operations. It is designed to streamline processes such as user authentication, medical record management, and inventory control. This project is part of the NTU SC2002 course.

### Documentation
You can access the project documentation, including JavaDoc, [here](https://github.com/YellowO2/HospitalManagementSystem/tree/main/doc).

### Assumptions
- There is only 1 user logging in at a time.
- The system assumes that the hospital operates from 9 am to 5 pm every day.
- User roles (e.g., Doctor, Patient, Administrator) are predefined and cannot be dynamically created within the system.
- Users are responsible for maintaining the confidentiality of their passwords to prevent unauthorized access. No additional multi-factor authentication will also be implemented.
- Doctors would need to write all the medicines needed in a single prescription.
- Doctors and Patients can only access appointments 7 days from the day itself.
- Any appointment slot that is selected is considered booked, lest it’s canceled.
- Medications are also assumed to have no expiry dates
- The system assumes a valid dataset for initialization. This includes users, medical records, and inventory data.
- All database operations are simulated using CSV files to emulate persistent storage.

### UML Diagram
- The UML diagrams can be assessed [here](https://github.com/YellowO2/HospitalManagementSystem/blob/main/UML%20Diagram).

### Project Contributors
- **Gong Yuelong**
- **Huang Yuxuan**
- **Joshua Tan (Chen Kaijun)**
- **Rizq Harith Bin Abdul Razak**
- **Sim Jian An**
