import numpy as np
import matplotlib.pyplot as plt
from sklearn import (datasets, svm, metrics)
from matplotlib.lines import Line2D # For the custom legend
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import ConfusionMatrixDisplay

def load_wdbc_data(filename):
    class WDBCData:
        data          = [] # Shape: (569, 30)
        target        = [] # Shape: (569, )
        target_names  = ['malignant', 'benign']
        feature_names = ['mean radius', 'mean texture', 'mean perimeter', 'mean area', 'mean smoothness', 'mean compactness', 'mean concavity', 'mean concave points', 'mean symmetry', 'mean fractal dimension',
                         'radius error', 'texture error', 'perimeter error', 'area error', 'smoothness error', 'compactness error', 'concavity error', 'concave points error', 'symmetry error', 'fractal dimension error',
                         'worst radius', 'worst texture', 'worst perimeter', 'worst area', 'worst smoothness', 'worst compactness', 'worst concavity', 'worst concave points', 'worst symmetry', 'worst fractal dimension']
    wdbc = WDBCData()
    with open(filename) as f:
        for line in f.readlines():
            items = line.split(',')
            
            # TODO #1) Add the true label (0 for M / 1 for others)
            # 'M' (malignant)을 0으로, 'B' (benign)을 1로 변환
            wdbc.target.append(0 if items[1] == 'M' else 1)
            
            # TODO #1) Add 30 attributes (as floating-point numbers)
            # 30개의 속성 문자열을 부동소수점 숫자로 변환하여 추가
            wdbc.data.append([float(attr) for attr in items[2:]])
            
        wdbc.data = np.array(wdbc.data)
        wdbc.target = np.array(wdbc.target) # target도 numpy array로 변환
    return wdbc

if __name__ == '__main__':
    # Load a dataset
    # TODO #1) Implement 'load_wdbc_data()'
    # wdbc.data 파일의 상대 경로가 올바른지 확인하세요. (예: 'data/wdbc.data' 또는 'w9/data/wdbc.data')
    try:
        wdbc = load_wdbc_data('data/wdbc.data')
    except FileNotFoundError:
        print("Error: 'data/wdbc.data' 파일을 찾을 수 없습니다.")
        print("파일 경로를 올바르게 수정해주세요. (예: 'w9/data/wdbc.data')")
        exit()


    # Train a model
    # model = svm.SVC()                           # TODO #2) Find a better classifier (SVC accuracy: 0.902)
    
    # === TODO #2 수정된 부분 ===
    # RandomForestClassifier로 변경 (일반적으로 SVC 기본값보다 성능이 좋음)
    # random_state=42를 추가하여 실행할 때마다 동일한 결과를 얻도록 보장
    model = RandomForestClassifier(n_estimators=100, random_state=42)
    # ==========================
    
    model.fit(wdbc.data, wdbc.target)

    # Test the model
    predict = model.predict(wdbc.data)
    accuracy = metrics.balanced_accuracy_score(wdbc.target, predict)

    # === TODO #3) Visualize the confusion matrix ===
    plt.figure() # 새로운 Figure 생성
    ConfusionMatrixDisplay.from_estimator(model, wdbc.data, wdbc.target,
                                          display_labels=wdbc.target_names,
                                          cmap=plt.cm.Blues)
    plt.title(f'Confusion Matrix (Accuracy: {accuracy:.3f})')
    plt.savefig('wdbc_classification_matrix.png') # 과제 요구사항 파일명으로 저장
    # ===============================================

    # Visualize testing results
    cmap = np.array([(1, 0, 0), (0, 1, 0)])
    clabel = [Line2D([0], [0], marker='o', lw=0, label=wdbc.target_names[i], color=cmap[i]) for i in range(len(cmap))]
    
    # (0, 1) 특성(mean radius, mean texture)에 대한 산점도 생성
    for (x, y) in [(0, 1)]: # Not mandatory, but try [(i, i+1) for i in range(0, 30, 2)]
        plt.figure()
        plt.title(f'My Classifier (Accuracy: {accuracy:.3f})')
        # wdbc.target이 int형 배열이어야 cmap[wdbc.target] 인덱싱이 가능합니다.
        plt.scatter(wdbc.data[:,x], wdbc.data[:,y], c=cmap[wdbc.target], edgecolors=cmap[predict])
        plt.xlabel(wdbc.feature_names[x])
        plt.ylabel(wdbc.feature_names[y])
        plt.legend(handles=clabel, framealpha=0.5)
        
        # === 산점도 그래프 저장 코드 추가 ===
        plt.savefig('wdbc_classification_scatter.png') # 과제 요구사항 파일명으로 저장
        # ====================================
        
    plt.show()