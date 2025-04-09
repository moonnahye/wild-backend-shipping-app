package com.example.demo.model.port;

public class Port {
    private PortId portId;

    public Port(PortId portId) {
        this.portId = portId;
    }

    public PortId getPortId() {
        return portId;
    }
}
