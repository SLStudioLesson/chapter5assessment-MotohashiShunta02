package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskapp.model.Task;
import com.taskapp.model.User;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();// 返すリスト

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // タイトル行飛ばし
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] value1 = line.split(",");

                User user = userDataAccess.findByCode(Integer.parseInt(value1[3]));

                Task task = new Task(
                        Integer.parseInt(value1[0]),
                        value1[1],
                        Integer.parseInt(value1[2]),
                        user);

                tasks.add(task);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * タスクをCSVに保存します。
     * 
     * @param task 保存するタスク
     */
    public void save(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            // 改行
            writer.newLine();

            // 書き込み
            writer.write(task.getCode() + "," +
                    task.getName() + "," +
                    task.getStatus() + "," +
                    task.getRepUser().getCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを1件取得します。
     * 
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
    public Task findByCode(int code) {
        Task task = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // タイトル飛ばし
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] value1 = line.split(",");

                int taskCode = Integer.parseInt(value1[0]);

                if (taskCode != code)
                    continue;

                User user = userDataAccess.findByCode(Integer.parseInt(value1[3]));

                task = new Task(taskCode, value1[1], Integer.parseInt(value1[2]), user);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * タスクデータを更新します。
     * 
     * @param updateTask 更新するタスク
     */
    public void update(Task updateTask) {
        List<Task> tasks = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Code,Name,Status,Rep_User_Code\n");

            for (Task task : tasks) {
                if (updateTask.getCode() == task.getCode()) {
                    writer.write(updateTask.getCode() + "," + updateTask.getName() + "," +
                            updateTask.getStatus() + "," + updateTask.getRepUser().getCode());
                } else {
                    writer.write(task.getCode() + "," + task.getName() + "," +
                            task.getStatus() + "," + task.getRepUser().getCode());
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを削除します。
     * 
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    // try () {

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * 
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
    // private String createLine(Task task) {
    // }
}