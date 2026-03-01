package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - mutable fields
 * - multiple constructors
 * - public setters
 * - tags list can be modified from outside
 * - validation is scattered elsewhere
 *
 * TODO (student): refactor to immutable + Builder.
 */
public class IncidentTicket {

    private String id;
    private String reporterEmail;
    private String title;

    private String description;
    private String priority;       // LOW, MEDIUM, HIGH, CRITICAL
    private List<String> tags;     // mutable leak
    private String assigneeEmail;
    private boolean customerVisible;
    private Integer slaMinutes;    // optional
    private String source;         // e.g. "CLI", "WEBHOOK", "EMAIL"

    public IncidentTicket() {
        this.tags = new ArrayList<>();
    }

    public IncidentTicket(String id, String reporterEmail, String title) {
        this();
        this.id = id;
        this.reporterEmail = reporterEmail;
        this.title = title;
    }

    public IncidentTicket(String id, String reporterEmail, String title, String priority) {
        this(id, reporterEmail, title);
        this.priority = priority;
    }

    // Getters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; } // BROKEN: leaks internal list
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    // Setters (BROKEN: should not exist after refactor)
    public void setId(String id) { this.id = id; }
    public void setReporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setTags(List<String> tags) { this.tags = tags; } // BROKEN: retains external reference
    public void setAssigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; }
    public void setCustomerVisible(boolean customerVisible) { this.customerVisible = customerVisible; }
    public void setSlaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }
    public static class Builder {
        private String id;
        private String reporterEmail;
        private String title;
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible;
        private Integer slaMinutes;
        private String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }
        public Builder tags(List<String> tags) { 
            if (tags != null) this.tags = new ArrayList<>(tags); 
            return this; 
        }
        public Builder assigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; return this; }
        public Builder customerVisible(boolean customerVisible) { this.customerVisible = customerVisible; return this; }
        public Builder slaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; return this; }
        public Builder source(String source) { this.source = source; return this; }

        public IncidentTicket build() {
            IncidentTicket ticket = new IncidentTicket();
            ticket.id = this.id;
            ticket.reporterEmail = this.reporterEmail;
            ticket.title = this.title;
            ticket.description = this.description;
            ticket.priority = this.priority;
            ticket.tags = Collections.unmodifiableList(this.tags); // immutable
            ticket.assigneeEmail = this.assigneeEmail;
            ticket.customerVisible = this.customerVisible;
            ticket.slaMinutes = this.slaMinutes;
            ticket.source = this.source;
            return ticket;
        }
    }

}
