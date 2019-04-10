package application.util;

public class CVSFileDetails {
    
    /** Creates a new instance of CVSFileDetails */
    public CVSFileDetails() {
    
    }
    
    private String revisionnumber;
    private String lockedby;
    private String revisiondate;
    private String author;
    private String state;
    private String lines;
    private String kopt;
    private String commitid;
    private String mergepoint;
    private String filename;
    private String branches;
    private String message;
    private String addedLines;
    private String removedLines;
    private String chamado;
    
    public String getRevisionnumber() {
        return revisionnumber;
    }

    public void setRevisionnumber(String revisionnumber) {
        this.revisionnumber = revisionnumber;
    }

    public String getLockedby() {
        return lockedby;
    }

    public void setLockedby(String lockedby) {
        this.lockedby = lockedby;
    }

    public String getRevisiondate() {
        return revisiondate;
    }

    public void setRevisiondate(String revisiondate) {
        this.revisiondate = revisiondate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLines() {
        return lines;
    }
    
    public String getAddedlines(){
        return addedLines;
    }
    
    public String getRemovedLines(){
        return removedLines;
    }    
    
    public void setLines(String lines) {
        this.lines = lines;
        if (!lines.equals("")){
            this.addedLines = lines.substring(0, lines.indexOf(" "));
            this.removedLines = lines.substring(lines.indexOf(" "), lines.length());
        }
    }

    public String getKopt() {
        return kopt;
    }

    public void setKopt(String kopt) {
        this.kopt = kopt;
    }

    public String getCommitid() {
        return commitid;
    }

    public void setCommitid(String commitid) {
        this.commitid = commitid;
    }

    public String getMergepoint() {
        return mergepoint;
    }

    public void setMergepoint(String mergepoint) {
        this.mergepoint = mergepoint;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        if (message.toLowerCase().indexOf("chamado #") > 0)
            this.chamado = message.substring(message.indexOf("#")+1, message.lastIndexOf("#"));
        else 
            this.chamado = "";
    }

    public String getChamado() {
        return chamado;
    }
    
}