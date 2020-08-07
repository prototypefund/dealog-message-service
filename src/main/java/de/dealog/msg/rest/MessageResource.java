package de.dealog.msg.rest;

import de.dealog.msg.persistence.Message;
import de.dealog.msg.service.MessageService;
import de.dealog.msg.service.PagedList;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Resource for {@link Message}s
 */
@Path(MessageResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    public static final String RESOURCE_PATH = "/messages";

    @Inject
    MessageConverter messageConverter;

    @Inject
    MessageService messageService;

    /**
     * Request {@link Message}s limited by...
     * @param pageRequest the page request page and page size
     * @return the list
     */
    @GET
    public Response getAll(@BeanParam final PageRequest pageRequest) {

        final PagedList<? extends Message> messages = messageService.list(pageRequest.getPage(), pageRequest.getSize());
        Iterable<MessageRest> messagesRest = messageConverter.convertAll(messages.getContent());

        return Response.ok(messagesRest).build();
    }
}
