package com.jobdetails.shant.getjobdetails.Common;

public class ConnectionProfile {

    private static String agentInfo = "";
    private static String authGateWayHNIP = "";
    private static int authGateWayPort = 0;

    public static String getAgentInfo() {
        return agentInfo;
    }

    public static void setAgentInfo(String agentInfo) {
        ConnectionProfile.agentInfo = agentInfo;
    }

    public static String getAuthGateWayHNIP() {
        return authGateWayHNIP;
    }

    public static void setAuthGateWayHNIP(String authGateWayHNIP) {
        ConnectionProfile.authGateWayHNIP = authGateWayHNIP;
    }

    public static int getAuthGateWayPort() {
        return authGateWayPort;
    }

    public static void setAuthGateWayPort(int authGateWayPort) {
        ConnectionProfile.authGateWayPort = authGateWayPort;
    }
}
