package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // scattered validation (incomplete on purpose)
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
        if (reporterEmail == null || !reporterEmail.contains("@")) throw new IllegalArgumentException("email invalid");
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title required");

        List<String> tags = new ArrayList<>();
        tags.add("NEW");
    
     return new IncidentTicket.Builder(id, reporterEmail, title)
            .priority("MEDIUM")
            .source("CLI")
            .customerVisible(false)
            .tags(tags)
            .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        // BAD: mutating ticket after it has been "created"
        List<String> tags = new ArrayList<>(t.getTags());
        tags.add("ESCALATED");

     return new IncidentTicket.Builder(t.getId(), t.getReporterEmail(), t.getTitle())
        .description(t.getDescription())
        .priority("CRITICAL")
        .assigneeEmail(t.getAssigneeEmail())
        .customerVisible(t.isCustomerVisible())
        .slaMinutes(t.getSlaMinutes())
        .source(t.getSource())
        .tags(tags)
        .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        // scattered validation
        if (assigneeEmail != null && !assigneeEmail.contains("@")) {
            throw new IllegalArgumentException("assigneeEmail invalid");
        }
        return new IncidentTicket.Builder(t.getId(), t.getReporterEmail(), t.getTitle())
        .description(t.getDescription())
        .priority(t.getPriority())
        .assigneeEmail(assigneeEmail)
        .customerVisible(t.isCustomerVisible())
        .slaMinutes(t.getSlaMinutes())
        .source(t.getSource())
        .tags(t.getTags())
        .build();
    }
}
