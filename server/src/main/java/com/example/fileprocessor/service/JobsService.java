package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.Jobs;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.JobsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobsService {
    private final JobsRepository jobsRepository;

    public Jobs save(Jobs jobs) {
        return jobsRepository.save(jobs);
    }

    public Optional<Jobs> findByKeyAndUser(UUID uuid, User user) {
        return jobsRepository.findByJobKeyAndUser(uuid, user);
    }

    public void delete(Long id) {
        jobsRepository.deleteById(id);
    }
}
