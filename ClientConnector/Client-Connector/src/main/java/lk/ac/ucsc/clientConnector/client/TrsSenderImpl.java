package lk.ac.ucsc.clientConnector.client;

import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.controller.FrontOfficeController;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.common.api.TrsSender;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.Mapper;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TrsSenderImpl implements TrsSender {
    private static Logger logger = LoggerFactory.getLogger(WebClient.class);
    private Converter converter;
    private FrontOfficeController frontOfficeController;
    private Mapper mapper;

    public TrsSenderImpl() {
        this.mapper = MapperFactory.getInstance().createMapper(30000, 100000);
    }



    @Override
    public void processTrsResponse(TrsMessage trsMessage) {
        mapper.processResponse(Long.parseLong(trsMessage.getUniqueReqId()), trsMessage.getOriginalMessage());
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setFrontOfficeController(FrontOfficeController frontOfficeController) {
        this.frontOfficeController = frontOfficeController;
    }

}
