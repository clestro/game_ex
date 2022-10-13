package utils;

import com.sun.net.httpserver.HttpServer;
import entity.Clan;
import entity.GoldHistory;
import entity.Task;
import org.json.JSONObject;
import service.ClanService;
import service.GoldHistoryService;
import service.TaskService;
import service.UserService;
import service.impl.ClanServiceImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.ApiUtils.splitQuery;

public class Controllers {
    public void startServer() {
        try {
            int serverPort = 8000;
            HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

            addClan(server);
            getClan(server);
            completeTask(server);
            addGoldToClanByUser(server);
            getGoldHistoryByClan(server);

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClan(HttpServer server) {
        server.createContext("/api/clan/add", (exchange -> {

            Thread thread = new Thread(() -> {

                if ("POST".equals(exchange.getRequestMethod())) {

                    InputStream inputStream = exchange.getRequestBody();
                    String requestBody = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject jsonObject = new JSONObject(requestBody);
                    String name = jsonObject.getString("name");

                    ClanService clanService = new ClanServiceImpl();

                    Clan clan = new Clan();

                    clan.setName(name);
                    String response;

                    long id = clanService.add(clan);

                    if (id > 0) {
                        response = "Successfully saved by id: " + id;
                    } else {
                        response = "Error when trying to save";
                    }

                    try {
                        exchange.sendResponseHeaders(201, response.getBytes().length);

                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                } else {
                    try {
                        exchange.sendResponseHeaders(405, -1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                exchange.close();
            });

            thread.start();
        }));
    }

    private void getClan(HttpServer server) {
        server.createContext("/api/clan/get", (exchange -> {

            Thread thread = new Thread(() -> {

                if ("GET".equals(exchange.getRequestMethod())) {

                    Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());

                    String id = params.get("id").stream().findFirst().orElse(null);

                    if (id != null) {
                        long clanId = Long.parseLong(id);
                        ClanService clanService = new ClanServiceImpl();

                        Clan clan = clanService.get(clanId);

                        if (clan.getId() != 0) {
                            String respText = String.format(clan.toString());
                            try {
                                exchange.sendResponseHeaders(200, respText.getBytes().length);
                                OutputStream output = exchange.getResponseBody();
                                output.write(respText.getBytes());
                                output.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                exchange.sendResponseHeaders(204, -1);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                } else {
                    try {
                        exchange.sendResponseHeaders(405, -1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                exchange.close();
            });
            thread.start();
        }));
    }

    private void completeTask(HttpServer server) {
        server.createContext("/api/task/complete", (exchange -> {

            Thread thread = new Thread(() -> {

                if ("POST".equals(exchange.getRequestMethod())) {
                    InputStream inputStream = exchange.getRequestBody();
                    String requestBody = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject jsonObject = new JSONObject(requestBody);
                    long clanId = jsonObject.getLong("clanId");
                    long taskId = jsonObject.getLong("taskId");
                    ClanService clanService = new ClanServiceImpl();
                    TaskService taskService = new TaskService(clanService);

                    taskService.completeTask(clanId, taskId);

                    String response = "Task successfully completed";

                    try {
                        exchange.sendResponseHeaders(201, response.getBytes().length);

                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
                        exchange.sendResponseHeaders(405, -1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                exchange.close();
            });
            thread.start();
        }));
    }

    private void addGoldToClanByUser(HttpServer server) {
        server.createContext("/api/user/add-gold", (exchange -> {

            Thread thread = new Thread(() -> {

                if ("POST".equals(exchange.getRequestMethod())) {
                    InputStream inputStream = exchange.getRequestBody();
                    String requestBody = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject jsonObject = new JSONObject(requestBody);
                    long userId = jsonObject.getLong("userId");
                    long clanId = jsonObject.getLong("clanId");
                    int gold = jsonObject.getInt("gold");
                    ClanService clanService = new ClanServiceImpl();
                    UserService userService = new UserService(clanService);

                    userService.addGoldToClan(userId, clanId, gold);

                    String response = "User with id - " + userId + " successfully added gold";

                    try {
                        exchange.sendResponseHeaders(201, response.getBytes().length);

                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
                        exchange.sendResponseHeaders(405, -1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                exchange.close();
            });
            thread.start();
        }));
    }

    private void getGoldHistoryByClan(HttpServer server) {
        server.createContext("/api/history/by-clan", (exchange -> {

            Thread thread = new Thread(() -> {

                if ("POST".equals(exchange.getRequestMethod())) {
                    InputStream inputStream = exchange.getRequestBody();
                    String requestBody = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject jsonObject = new JSONObject(requestBody);
                    long clanId = jsonObject.getLong("clanId");

                    GoldHistoryService goldHistoryService = new GoldHistoryService();

                    String response = "";

                    for (GoldHistory history : goldHistoryService.getAllByClan(clanId)) {
                        response += history.toString() + "\n";
                    }

                    try {
                        exchange.sendResponseHeaders(201, response.getBytes().length);

                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
                        exchange.sendResponseHeaders(405, -1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                exchange.close();
            });
            thread.start();
        }));
    }
}
