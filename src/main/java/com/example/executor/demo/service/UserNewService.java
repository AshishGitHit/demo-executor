package com.example.executor.demo.service;

import com.example.executor.demo.entity.UserNew;
import com.example.executor.demo.repository.UserNewRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserNewService {

    @Autowired
    private UserNewRepo repo;

    Object target;
    Logger logger = LoggerFactory.getLogger(UserNewService.class);

    @Async
    public CompletableFuture<List<UserNew>> saveUsers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<UserNew> users = parseCSVFile(file);
        logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
        users = repo.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(users);
    }
    @Async
    public CompletableFuture<List<UserNew>> findAllUsers(){
        logger.info("get list of user by "+Thread.currentThread().getName());
        List<UserNew> users=repo.findAll();
        return CompletableFuture.completedFuture(users);
    }

    private List<UserNew> parseCSVFile(final MultipartFile file) throws Exception {
        final List<UserNew> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final UserNew user = new UserNew();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}

