package PerformanceTests;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Calendar;

import com.sun.management.OperatingSystemMXBean;
import java.lang.Runtime;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import java.util.Random;

public class PerformanceTests {


    public static void main(String[] args) throws IOException, InterruptedException {
        // Create Todos
        FileWriter create_todo_time_file = new FileWriter("csv_for_graphs/create_todo_time.csv");
        create_todo_time_file.append("Number Objects, Time (ms)\n");

        FileWriter create_todo_cpu_usage = new FileWriter("csv_for_graphs/create_todo_cpu.csv");
        create_todo_cpu_usage.append("Number Objects ,CPU Usage\n");

        FileWriter create_todo_memory_usage = new FileWriter("csv_for_graphs/create_todo_mem.csv");
        create_todo_memory_usage.append("Number Objects, Memory Usage (MB)\n");



        FileWriter edit_todo_time = new FileWriter("csv_for_graphs/edit_todo_time.csv");
        edit_todo_time.append("Number Objects,Time (ms)\n");

        FileWriter edit_todo_cpu_usage = new FileWriter("csv_for_graphs/edit_todo_cpu.csv");
        edit_todo_cpu_usage.append("Number Objects,CPU Usage\n");

        FileWriter edit_todo_memory_usage = new FileWriter("csv_for_graphs/edit_todo_mem.csv");
        edit_todo_memory_usage.append("Number Objects,Memory Usage (MB)\n");



        FileWriter delete_todo_time = new FileWriter("csv_for_graphs/delete_todo_time.csv");
        delete_todo_time.append("Number Objects, Time (ms)\n");

        FileWriter delete_todo_cpu_usage = new FileWriter("csv_for_graphs/delete_todo_cpu.csv");
        delete_todo_cpu_usage.append("Number Objects, CPU Usage\n");

        FileWriter delete_todo_memory_usage = new FileWriter("csv_for_graphs/delete_todo_mem.csv");
        delete_todo_memory_usage.append("Number Objects, Memory Usage (MB)\n");

        // TODOS
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        int number_objects = 1;
        Todos todos = new Todos();

        for (int i = 0; i < 100; i++) {

            // Create todo
            long start_time_create;
            long end_time_create;
            start_time_create = Calendar.getInstance().getTimeInMillis();
            todos.createTodo();
            end_time_create = Calendar.getInstance().getTimeInMillis();
            long time_create = end_time_create - start_time_create;

            create_todo_time_file.append(number_objects + "," + time_create + "\n");
            create_todo_time_file.flush();

            double cpu_load = osBean.getProcessCpuLoad() * 100;
            create_todo_cpu_usage.append(number_objects + "," + cpu_load + "\n");
            create_todo_cpu_usage.flush();

            long used_memory_create = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double used_memory_create_mb = used_memory_create / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            create_todo_memory_usage.append(number_objects + "," + used_memory_create_mb + "\n");
            create_todo_memory_usage.flush();

            // Modify todo
            long start_time_update;
            long end_time_update;
            start_time_update = Calendar.getInstance().getTimeInMillis();
            todos.updateTodo();
            end_time_update = Calendar.getInstance().getTimeInMillis();
            long update_time = end_time_update - start_time_update;

            edit_todo_time.append(number_objects + "," + update_time + "\n");
            edit_todo_time.flush();

            double update_cpu = osBean.getProcessCpuLoad() * 100;
            edit_todo_cpu_usage.append(number_objects + "," + update_cpu + "\n");
            edit_todo_cpu_usage.flush();

            double edit_memory_update = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double edit_memory_update_mb = edit_memory_update / (1024.0 * 1024.0);
            edit_todo_memory_usage.append(number_objects + "," + edit_memory_update_mb + "\n");
            edit_todo_memory_usage.flush();

            // Delete todo
            long start_time_delete;
            long end_time_delete;
            start_time_delete = Calendar.getInstance().getTimeInMillis();
            todos.deleteTodo();
            end_time_delete = Calendar.getInstance().getTimeInMillis();
            long time_delete = end_time_delete - start_time_delete;

            delete_todo_time.append(number_objects + "," + (time_delete) + "\n");
            delete_todo_time.flush();

            double cpu_load_delete = osBean.getProcessCpuLoad() * 100;
            delete_todo_cpu_usage.append(number_objects + "," + cpu_load_delete + "\n");
            delete_todo_cpu_usage.flush();

            long delete_memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            double delete_memory_mb = delete_memory / (1024.0 * 1024.0);
            delete_todo_memory_usage.append(number_objects + "," + delete_memory + "\n");
            delete_todo_memory_usage.flush();

            number_objects++;

        }

    }
}
