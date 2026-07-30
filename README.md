# Smart Task Scheduler & Workflow Automation Engine

A high-performance, standalone console-based task scheduling and workflow engine built in pure Java (JDK 11+). Designed with multi-module architecture, robust Data Structures & Algorithms (DSA), enterprise design patterns, and asynchronous background execution.

---

## 🌟 Key Features & Core Capabilities

- **User Authentication & Role-Based Access Control (RBAC)**: SHA-256 password hashing, user session handling, and role permissions (`ADMIN`, `DEVELOPER`, `USER`).
- **Task Management Engine**: Full task lifecycle tracking, custom categories, due dates, estimated durations, and priority weights built using the **Builder Pattern**.
- **Undo / Redo System**: Transactional action tracking using the **Command Pattern** and stack-based history.
- **Workflow Engine & Dependency Graph**:
  - Directed Graph adjacency list representation for task dependencies.
  - **Topological Sorting (Kahn's Algorithm)** for optimal task execution ordering and cycle detection (`DependencyCycleException`).
  - Graph traversals using **Breadth-First Search (BFS)** and **Depth-First Search (DFS)**.
  - Strategy Pattern execution pipeline.
- **Task Scheduler & Background Execution**:
  - Asynchronous task worker thread pool using `ScheduledExecutorService` and `ExecutorService`.
  - Producer-Consumer queue design pattern for immediate task offloading.
  - **Greedy Optimization Algorithm** for schedule ordering based on priority weights, due dates, and duration.
- **Custom Data Structures & Algorithms**:
  - Custom `MinHeap` & `MaxHeap` implementations.
  - Custom `Stack` & `Queue` data structures.
  - Custom **Trie** data structure for high-speed prefix search on task titles and tags.
  - Priority Queue manager.
- **Advanced Search & Sorting**:
  - **KMP (Knuth-Morris-Pratt)** string pattern search algorithm for title/description matching.
  - **Binary Search** for quick task lookup by ID.
  - **QuickSort** & **MergeSort** algorithms for sorting tasks by priority and duration.
- **Notification Engine**:
  - **Observer Pattern** (`TaskObserver`) dispatching real-time notifications to console listeners and audit logs.
- **Analytics Dashboard**:
  - **Sliding Window Algorithm** to calculate 7-day and 30-day rolling completion rates and workload metrics.
- **Reporting & Persistence**:
  - Exporters for **CSV**, **JSON**, and formatted **Text** reports.
  - Binary object serialization for data persistence across restarts.

---

## 🏗️ Architecture & Module Structure

```
src/
└── com/
    └── smart/
        └── scheduler/
            ├── Main.java                        # Application Entry Point
            ├── auth/                            # User Authentication & RBAC
            ├── task/                            # Task Domain & Builder Pattern
            ├── command/                         # Command Pattern (Undo/Redo)
            ├── workflow/                        # Directed Graph & Topological Sort
            ├── scheduler/                       # Thread Pools & Background Workers
            ├── dsa/                             # Custom Heaps, Trie, Stack, Queue
            ├── search/                          # KMP, Binary Search, QuickSort, MergeSort
            ├── notification/                    # Observer Pattern & Event Listeners
            ├── analytics/                       # Sliding Window Metrics Engine
            ├── logger/                          # Custom Thread-Safe Logger
            ├── reporting/                       # CSV, JSON, Text Exporters
            ├── persistence/                     # File Handling & Serialization
            ├── exception/                       # Custom Domain Exceptions
            └── ui/                              # Interactive CLI Menu System
```

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK 11 or higher)** installed and added to PATH.

### Building the Project
From the repository root directory, run:

```powershell
javac -d bin (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
```

### Running the Application
Launch the interactive console application:

```powershell
java -cp bin com.smart.scheduler.Main
```

---

## 📊 Design Patterns & DSA Summary

| Category | Components Implemented |
| :--- | :--- |
| **Design Patterns** | Builder, Command (Undo/Redo), Strategy, Observer, Singleton, Factory |
| **Data Structures** | Graph, MinHeap, MaxHeap, Trie, PriorityQueue, Stack, Queue, HashMap, HashSet |
| **Algorithms** | Topological Sort (BFS/Kahn's), DFS, KMP Pattern Matching, Binary Search, QuickSort, MergeSort, Sliding Window, Greedy Scheduling |

---

## ✒️ Author & License

Developed by **[Riya Sajnani](https://github.com/Riya-Sajnani)**.
Open-source software released under the MIT License.
