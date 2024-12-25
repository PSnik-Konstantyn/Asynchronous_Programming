package org.example.pz5;

public class Route {
    String transport;
    int durationHours;
    int cost;

    public Route(String transport, int durationHours, int cost) {
        this.transport = transport;
        this.durationHours = durationHours;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return transport + " (" + durationHours + " год, " + cost + " грн)";
    }
}