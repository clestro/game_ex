import entity.Task;
import entity.User;
import service.TaskService;
import service.UserService;
import service.impl.ClanServiceImpl;
import utils.Controllers;

public class GameExampleApplication {
    public static void main(String[] args) {

        new Controllers().startServer();

        System.out.println("Program starts successfully!");

        System.out.println("\nTasks list :");

        TaskService taskService = new TaskService(new ClanServiceImpl());

        for (Task task : taskService.getAll()) {
            System.out.println(task.toString());
        }

        System.out.println("\nUsers list :");

        UserService userService = new UserService(new ClanServiceImpl());

        for (User user : userService.getAll()) {
            System.out.println(user.toString());
        }

    }
}
