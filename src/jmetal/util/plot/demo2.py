import matplotlib.pyplot as plt
import numpy as np
import sys
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.cm as cm

if __name__ == '__main__':
    dimensiony = int(sys.argv[1])
    name = sys.argv[2]
    dimensionx = (len(sys.argv) - 3) / dimensiony
    a = np.zeros((dimensionx, dimensiony))
    i = 3;
    while (i < len(sys.argv)):
        for j in range(dimensiony):
            a[(i - 3) / dimensiony][j] = float(sys.argv[i])
            i += 1
    plt.style.use('ggplot')
    t=np.arange(a.shape[0])
    if (dimensiony == 3):
        # plt.figure(figsize=(12,8),dpi=200)
        fig = plt.figure(figsize=(8, 6), dpi=100)
        ax = Axes3D(fig)
        ax.scatter(a[:, 0], a[:, 1], a[:, 2], c=t,cmap='Dark2')
        plt.xlabel('x')
        plt.ylabel("y")
        plt.title(name)
        ax.view_init(32, 45)
        plt.show()
        plt.close()
    elif (dimensiony == 2):
        plt.figure(figsize=(12, 8), dpi=80)
        plt.scatter(a[:, 0], a[:, 1],c=t,cmap='Dark2')
        plt.xlabel('x')
        plt.ylabel("y")
        plt.title(name)
        plt.show()
    else:
        fig = plt.figure(figsize=(8, 6), dpi=100)
        x = np.tile(np.array(range(a.shape[1])), (a.shape[0], 1)).T
        a = a.T
        plt.plot(x[:, 0] + 1, a[:, 0], label="labels")
        plt.plot(x[:, 1:] + 1, a[:, 1:])
        plt.xlabel('Dimension Number')
        plt.ylabel("Values")
        plt.xticks(range(1, dimensiony + 1))
        plt.title(name)
        plt.show()
