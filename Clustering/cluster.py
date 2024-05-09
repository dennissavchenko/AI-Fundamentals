import random
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt


class Cluster:
    def __init__(self, file_path):
        self.file_path = file_path
        self.data = []
        self.centers = []
        self.distances = []
        self.read_csv()

    def classify(self, point):
        if len(point) != len(self.data[0]) - 1:
            print("Length mismatch")
        else:
            dist = []
            for center in self.centers:
                dist.append(self.calculate_distance(point, center))
            return self.find_index_of_min(dist)

    def percentage_of_class_per_cluster(self, k):
        print()
        print("<---------------Percentage of class per cluster--------------->")
        initial_data = []
        with open(self.file_path, 'r') as file:
            for line in file:
                row = line.strip().split(',')
                initial_data.append(row)
        for i in range(k):
            m = dict()
            counter = 0
            for j in range(len(self.data)):
                if self.data[j][:-1] == [float(value) for value in initial_data[j][:-1]] and self.data[j][-1] == i:
                    m[initial_data[j][-1]] = m.get(initial_data[j][-1], 0) + 1
                    counter += 1
            print(f'Cluster {i}: ')
            for key in m.keys():
                print(f'{key}: {round((m[key] / counter) * 100, 2)}%')
            print()

    def group(self, k):
        print()
        print("<---------------Sum of all distances at every iteration--------------->")
        self.random_initialization(k)
        counter = 1
        itr = 0
        while counter > 0:
            self.recalculate_centers(k)
            print(f'Iteration {itr}: {self.recalculate_distances()}')
            counter = self.reassign_points()
            itr += 1
        self.percentage_of_class_per_cluster(k)


    def create_pair_plot(self):
        # for i in range(len(self.centers)):
        #     center = self.centers[i]
        #     center.append(f'center of {i}')
        #     self.data.append(center)
        df = pd.DataFrame(self.data, columns=[f'Feature_{i + 1}' for i in range(len(self.data[0]) - 1)] + ['Cluster'])
        sns.pairplot(df, hue='Cluster', palette='Dark2')
        plt.show()

    def print_members(self):
        for k in range(len(self.centers)):
            print(f'Class {k}: ')
            for point in self.data:
                if point[-1] == k:
                    print(point[:-1])

    def read_csv(self):
        with open(self.file_path, 'r') as file:
            for line in file:
                row = [float(value) for value in line.split(',')[:-1]]
                row.append(0)
                self.data.append(row)

    def random_initialization(self, k):
        self.centers = []
        for row in self.data:
            row[-1] = random.randint(0, k - 1)

    def reassign_points(self):
        counter = 0
        for i in range(len(self.data)):
            new_class = self.find_index_of_min(self.distances[i])
            if new_class != self.data[i][-1]:
                counter += 1
            self.data[i][-1] = new_class
        return counter

    @staticmethod
    def find_index_of_min(row):
        min_value = row[0]
        min_index = 0
        for i in range(1, len(row)):
            if row[i] < min_value:
                min_value = row[i]
                min_index = i
        return min_index

    def calculate_average(self, i):
        avg = [0] * (len(self.data[0]) - 1)
        counter = 0
        for row in self.data:
            if row[-1] == i:
                counter += 1
                for j in range(len(row) - 1):
                    avg[j] += float(row[j])
        if counter > 0:
            for i in range(len(avg)):
                avg[i] /= counter
        return avg

    def recalculate_centers(self, k):
        if not self.centers:
            for i in range(k):
                self.centers.append(self.calculate_average(i))
        else:
            for i in range(k):
                self.centers[i] = self.calculate_average(i)

    def recalculate_distances(self):
        total = 0
        self.distances = []
        for point in self.data:
            distances_one_point = []
            for center in self.centers:
                distances_one_point.append(self.calculate_distance(center, point))
            self.distances.append(distances_one_point)
        for i in range(len(self.distances)):
            total += self.distances[i][self.data[i][-1]]
        return total

    @staticmethod
    def calculate_distance(center, point):
        dist = 0
        for i in range(len(center)):
            dist += (center[i] - point[i]) ** 2
        return dist

    def get_data(self):
        return self.data

    def get_centers(self):
        return self.centers

    def get_distances(self):
        return self.distances
