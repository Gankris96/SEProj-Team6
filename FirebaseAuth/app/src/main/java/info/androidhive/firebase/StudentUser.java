package info.androidhive.firebase;

/**
 * Created by Ganesh on 10/19/2017.
 */

public class StudentUser {
    String studentUserId;
    String studentName;
    String studentEmail;
    String studentPass;
    String studentUSN;
    String studentAddr;
    String studentPhno;
    String studentGender;
    Boolean busAllocated;

    public StudentUser(){

    }
    public StudentUser(String studentUserId, String studentName, String studentEmail, String studentPass, String studentUSN, String studentAddr, String studentPhno, String studentGender, Boolean busAllocated) {
        this.studentUserId = studentUserId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPass = studentPass;
        this.studentUSN = studentUSN;
        this.studentAddr = studentAddr;
        this.studentPhno = studentPhno;
        this.studentGender = studentGender;
        this.busAllocated = busAllocated;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPass() {
        return studentPass;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }

    public String getStudentUSN() {
        return studentUSN;
    }

    public void setStudentUSN(String studentUSN) {
        this.studentUSN = studentUSN;
    }

    public String getStudentAddr() {
        return studentAddr;
    }

    public void setStudentAddr(String studentAddr) {
        this.studentAddr = studentAddr;
    }

    public String getStudentPhno() {
        return studentPhno;
    }

    public void setStudentPhno(String studentPhno) {
        this.studentPhno = studentPhno;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public Boolean getAllocationStatus() {
        return busAllocated;
    }

    public void setAllocationStatus(Boolean status) { this.busAllocated = status; }

}
