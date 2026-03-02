package com.example.tickets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final String priority;       // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;     // mutable leak
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;    // optional
    private final String source;         // e.g. "CLI", "WEBHOOK", "EMAIL"

    // Private constructor — only Builder can create tickets
    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;

        // Defensive copy of tags (prevents mutation)
        this.tags = List.copyOf(builder.tags);
    }

    // Getters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; } // already immutable
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

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

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder().from(this);
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

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder reporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }
        public Builder tags(List<String> tags) { if (tags != null) this.tags = new ArrayList<>(tags); return this; }
        public Builder addTag(String tag) { this.tags.add(tag); return this; }
        public Builder assigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; return this; }
        public Builder customerVisible(boolean customerVisible) { this.customerVisible = customerVisible; return this; }
        public Builder slaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; return this; }
        public Builder source(String source) { this.source = source; return this; }

        public Builder from(IncidentTicket ticket) {
            this.id = ticket.id;
            this.reporterEmail = ticket.reporterEmail;
            this.title = ticket.title;
            this.description = ticket.description;
            this.priority = ticket.priority;
            this.tags = new ArrayList<>(ticket.tags);
            this.assigneeEmail = ticket.assigneeEmail;
            this.customerVisible = ticket.customerVisible;
            this.slaMinutes = ticket.slaMinutes;
            this.source = ticket.source;
            return this;
        }

        public IncidentTicket build() {

            if (id == null || id.isBlank() || id.length() > 20 || !id.matches("^[A-Z0-9-]+$")) {
                throw new IllegalArgumentException("id invalid");
            }

            if (reporterEmail == null || !reporterEmail.matches("^[^@]+@[^@]+\\.[^@]+$")) {
                throw new IllegalArgumentException("reporterEmail invalid");
            }

            if (title == null || title.isBlank() || title.length() > 80) {
                throw new IllegalArgumentException("title invalid");
            }

            if (assigneeEmail != null && !assigneeEmail.matches("^[^@]+@[^@]+\\.[^@]+$")) {
                throw new IllegalArgumentException("assigneeEmail invalid");
            }

            if (priority != null && !Set.of("LOW","MEDIUM","HIGH","CRITICAL").contains(priority)) {
                throw new IllegalArgumentException("priority invalid");
            }

            if (slaMinutes != null && (slaMinutes < 5 || slaMinutes > 7200)) {
                throw new IllegalArgumentException("slaMinutes invalid");
            }

            return new IncidentTicket(this);
        }
    }

}
