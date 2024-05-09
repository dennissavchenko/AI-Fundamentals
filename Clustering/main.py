import random
from cluster import Cluster
from UI import UI
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns


iris_train = pd.read_csv("iris.data", names=["sepal_length", "sepal_width", "petal_length", "petal_width", "class"])
sns.pairplot(pd.concat([iris_train]), hue="class")
plt.show()

ui = UI()

ui.start()
