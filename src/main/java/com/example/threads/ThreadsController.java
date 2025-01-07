package com.example.threads;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ThreadsController is a REST controller that handles HTTP requests related to thread operations.
 * It provides endpoints to get the current thread's name and set a new name for the current thread.
 * 
 * Endpoints:
 * - GET /thread/name: Returns the name of the current thread.
 * - POST /thread/name/{name}: Sets the name of the current thread to the provided name and returns a confirmation message.
 * 
 * Example usage:
 * - GET /thread/name: "Thread[main,5,main]"
 * - POST /thread/name/myThread: "New name is: myThread"
 * 
 * Note: Changing the thread name affects only the current thread handling the request.
 * 
 * @see Thread
 */
@RestController
@RequestMapping("/thread")
public class ThreadsController {
    
    /**
     * Handles GET requests to the "/name" endpoint.
     * 
     * @return A string representation of the current thread.
     */
    @GetMapping("/name")
    public String getThreadName() {
        return Thread.currentThread().toString();
    }

    /**
     * Sets the name of the current thread to the provided name and returns a confirmation message.
     *
     * @param name the new name for the current thread
     * @return a confirmation message indicating the new name of the thread
     */
    @PostMapping("/name/{name}")
    public String saveByName(@PathVariable String name) {
        Thread.currentThread().setName(name);
        return "New name is: " + name;
    }
}