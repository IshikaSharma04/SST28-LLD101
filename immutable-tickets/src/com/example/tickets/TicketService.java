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
        // now relies on Builder validation
        List<String> tags = new ArrayList<>();
        tags.add("NEW");

        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(tags)
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        // create a new ticket via toBuilder() — no mutation
        List<String> tags = new ArrayList<>(t.getTags());
        tags.add("ESCALATED");

        return t.toBuilder()
                .priority("CRITICAL")
                .tags(tags)
                .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        // create a new ticket via toBuilder() — no mutation
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
