package com.example.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThreadsApplicationTests {

	@Test
	void expressao1() {
		String fuits[] = {"banana", "maça", "laranja", "uva", "pera"};
        for (String string : fuits) {
            System.out.println(string);
        }
        String[] fuits2 = {"banana", "maça", "laranja", "uva", "pera"};
        for (String string : fuits2) {
            System.out.println(string);
        }
	}

	@Test
	void contextLoads() {
		ConcurrentMap<Integer, Integer> totals = new ConcurrentHashMap<>();
		List<CompletableFuture<Void>> features = new ArrayList<>();
		Map<Integer, String> lines = Map.of(1, "Hello world hello", 2, "code compile test", 3, "error ERROR Error");
		for (Entry<Integer, String> entry : lines.entrySet()) {
			features.add(CompletableFuture.runAsync(() -> {
				char[] chars = String.join("", entry.getValue().split(" ")).toCharArray();
				int words = 0;
				for (char c : chars) {
					int qtde = 0;
					for (char a : chars) {
						if (String.valueOf(c).equalsIgnoreCase(String.valueOf(a)))
							qtde++;
					}

					if (qtde == 1)
						words++;
				}

				totals.put(entry.getKey(), words);
			}));
		}

		CompletableFuture.allOf(features.toArray(new CompletableFuture[0])).join();
		for (Entry<Integer, Integer> entry : totals.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

}
