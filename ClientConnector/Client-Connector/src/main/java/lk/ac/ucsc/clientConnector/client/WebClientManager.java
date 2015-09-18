package lk.ac.ucsc.clientConnector.client;

import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.ClientManager;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.api.MessageRouter;
import lk.ac.ucsc.clientConnector.common.api.ClientType;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.Mapper;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.MapperFactory;
import lk.ac.ucsc.clientConnector.settings.Settings;

import javax.servlet.http.HttpSession;


public class WebClientManager implements ClientManager {

    private MessageRouter messageRouter;
    private Converter converter;
    private ClientType clientType;
    private Settings settings;
    private Mapper mapper;

    public WebClientManager(Settings settings, MessageRouter messageRouter, Converter converter, ClientType clientType) {
        this.settings = settings;
        this.messageRouter = messageRouter;
        this.converter = converter;
        this.clientType = clientType;
        this.mapper = MapperFactory.getInstance().createMapper(settings.getMessageTimeout(), 100000);
    }

    @Override
    public void start() {
        // web server is started by web server controller since all web client managers use the same web server
    }

    @Override
    public void stop() {
        // web server is stopped by web server controller
    }

    @Override
    public Client createClient(Object session) {
        return new WebClient(settings.getTrsId(), (HttpSession) session, converter, mapper, messageRouter, clientType,
                 settings);
    }

    @Override
    public void setRequestHandler(Object requestHandler) {
        // Not required for web clients (requests are handled by a servlet)
    }
}
