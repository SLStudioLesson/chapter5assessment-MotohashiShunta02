package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * 
     * @param email    メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {

        User user = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // タイトル飛ばし
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] value1 = line.split(",");

                if (!(value1[2].equals(email) && value1[3].equals(password))) {// 一致しなかったらやり直し
                    continue;
                }

                user = new User(
                        Integer.parseInt(value1[0]),
                        value1[1],
                        value1[2],
                        value1[3]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * 
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        User user = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // タイトル飛ばし
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] value1 = line.split(",");

                if (Integer.parseInt(value1[0]) != code) {// 一致しなかったらやり直し
                    continue;
                }

                user = new User(
                        Integer.parseInt(value1[0]),
                        value1[1],
                        value1[2],
                        value1[3]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
