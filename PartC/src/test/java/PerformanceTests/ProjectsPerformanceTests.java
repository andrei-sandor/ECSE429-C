package PerformanceTests;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Calendar;

import com.sun.management.OperatingSystemMXBean;

public class ProjectsPerformanceTests {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileWriter create_project_time_file = new FileWriter("projects_csv/create_project_time.csv");
        create_project_time_file.append("Number Objects, Time (ms)\n");

        FileWriter create_project_cpu_usage = new FileWriter("projects_csv/create_project_cpu.csv");
        create_project_cpu_usage.append("Number Objects, CPU Usage\n");

        FileWriter create_project_memory_usage = new FileWriter("projects_csv/create_project_mem.csv");
        create_project_memory_usage.append("Number Objects, Memory Usage (MB)\n");

        FileWriter edit_project_time = new FileWriter("projects_csv/edit_project_time.csv");
        edit_project_time.append("Number Objects, Time (ms)\n");

        FileWriter edit_project_cpu_usage = new FileWriter("projects_csv/edit_project_cpu.csv");
        edit_project_cpu_usage.append("Number Objects, CPU Usage\n");

        FileWriter edit_project_memory_usage = new FileWriter("projects_csv/edit_project_mem.csv");
        edit_project_memory_usage.append("Number Objects, Memory Usage (MB)\n");

        FileWriter delete_project_time = new FileWriter("projects_csv/delete_project_time.csv");
        delete_project_time.append("Number Objects, Time (ms)\n");

        FileWriter delete_project_cpu_usage = new FileWriter("projects_csv/delete_project_cpu.csv");
        delete_project_cpu_usage.append("Number Objects, CPU Usage\n");

        FileWriter delete_project_memory_usage = new FileWriter("projects_csv/delete_project_mem.csv");
        delete_project_memory_usage.append("Number Objects, Memory Usage (MB)\n");

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        int number_objects = 1;
        ProjectsUnitTests projects = new ProjectsUnitTests();

        for (int i = 0; i < 100; i++) {
            long start_time_create;
            long end_time_create;
            start_time_create = Calendar.getInstance().getTimeInMillis();
            projects.createProject();
            end_time_create = Calendar.getInstance().getTimeInMillis();
            long time_create = end_time_create - start_time_create;

            create_project_time_file.append(number_objects + "," + time_create + "\n");
            create_project_time_file.flush();

            double cpu_load = osBean.getProcessCpuLoad() * 100;
            create_project_cpu_usage.append(number_objects + "," + cpu_load + "\n");
            create_project_cpu_usage.flush();

            long used_memory_create = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double used_memory_create_mb = used_memory_create / (1024.0 * 1024.0);
            create_project_memory_usage.append(number_objects + "," + used_memory_create_mb + "\n");
            create_project_memory_usage.flush();

            long start_time_update;
            long end_time_update;
            start_time_update = Calendar.getInstance().getTimeInMillis();
            projects.updateProject();
            end_time_update = Calendar.getInstance().getTimeInMillis();
            long update_time = end_time_update - start_time_update;

            edit_project_time.append(number_objects + "," + update_time + "\n");
            edit_project_time.flush();

            double update_cpu = osBean.getProcessCpuLoad() * 100;
            edit_project_cpu_usage.append(number_objects + "," + update_cpu + "\n");
            edit_project_cpu_usage.flush();

            double edit_memory_update = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double edit_memory_update_mb = edit_memory_update / (1024.0 * 1024.0);
            edit_project_memory_usage.append(number_objects + "," + edit_memory_update_mb + "\n");
            edit_project_memory_usage.flush();

            long start_time_delete;
            long end_time_delete;
            start_time_delete = Calendar.getInstance().getTimeInMillis();
            projects.deleteProject();
            end_time_delete = Calendar.getInstance().getTimeInMillis();
            long time_delete = end_time_delete - start_time_delete;

            delete_project_time.append(number_objects + "," + (time_delete) + "\n");
            delete_project_time.flush();

            double cpu_load_delete = osBean.getProcessCpuLoad() * 100;
            delete_project_cpu_usage.append(number_objects + "," + cpu_load_delete + "\n");
            delete_project_cpu_usage.flush();

            long delete_memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double delete_memory_mb = delete_memory / (1024.0 * 1024.0);
            delete_project_memory_usage.append(number_objects + "," + delete_memory_mb + "\n");
            delete_project_memory_usage.flush();

            number_objects++;
        }
    }
}

