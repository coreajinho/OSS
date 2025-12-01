import csv
import matplotlib.pyplot as plt

def read_csv(filename):
    midterm = []
    final = []
    with open(filename, 'r', encoding='utf-8') as f:
        reader = csv.reader(f)
        next(reader)
        for row in reader:
            try:
                midterm.append(int(row[0]))
                final.append(int(row[1]))
            except (ValueError, IndexError) as e:
                print(f"Warning: Skipping invalid row in {filename}: {row} - Error: {e}")
    return midterm, final

def calculate_total_score(midterm, final):
    if len(midterm) != len(final):
        raise ValueError("Midterm and final score lists must have the same length.")

    total = [(m * 0.4) + (f * 0.6) for m, f in zip(midterm, final)]
    return total

def plot_scatter(midterm_kr, final_kr, midterm_en, final_en):
    plt.figure(figsize=(10, 8))
    
    plt.scatter(midterm_kr, final_kr, color='red', marker='.', label='Korean Class')
    
    plt.scatter(midterm_en, final_en, color='blue', marker='+', label='English Class')
    
    plt.title('Midterm vs Final Scores', fontsize=16)
    plt.xlabel('Midterm Scores', fontsize=12)
    plt.ylabel('Final Scores', fontsize=12)
    plt.legend()
    plt.grid(True)
    
    plt.savefig('class_score_scatter.png')
    print("Scatter plot saved to class_score_scatter.png")
    plt.close()

def plot_hist(total_kr, total_en):
    plt.figure(figsize=(10, 8))
    
    bins = range(0, 101, 10)

    plt.hist(total_kr, bins=bins, color='red', alpha=0.7, label='Korean Class', edgecolor='black')
    
    plt.hist(total_en, bins=bins, color='blue', alpha=0.5, label='English Class', edgecolor='black')
    
    plt.title('Distribution of Total Scores', fontsize=16)
    plt.xlabel('Total Scores', fontsize=12)
    plt.ylabel('Number of Students', fontsize=12)
    plt.legend()
    plt.grid(axis='y', linestyle='--', alpha=0.7)

    plt.savefig('class_score_hist.png')
    print("Histogram saved to class_score_hist.png")
    plt.close() 

# --- 메인 실행 블록 ---
if __name__ == '__main__':
    midterm_kr, final_kr = read_csv('data/class_score_kr.csv')
    midterm_en, final_en = read_csv('data/class_score_en.csv')

    total_kr = calculate_total_score(midterm_kr, final_kr)
    total_en = calculate_total_score(midterm_en, final_en)

    plot_scatter(midterm_kr, final_kr, midterm_en, final_en)
    
    plot_hist(total_kr, total_en)