def read_data(filename):
    data = []
    with open(filename,'r') as fi:
        fi.readline()
        for line in fi.readlines():
            row = []
            nums = line.strip().split(',')
            for num in nums:
                row.append(int(num.strip()))
            data.append(row)
    return data

def calc_weighted_average(data_2d, weight):
    average = []
    for datum in data_2d:
        datumAvg = datum[0]*weight[0] + datum[1]* weight[1]
        average.append(datumAvg)
    return average

def analyze_data(data_1d):
    mean = 0
    var = 0
    median = 0
    sum =0
    sqrSum = 0
    for datum in data_1d:
        sum += datum
        sqrSum += datum**2
    mean = sum / len(data_1d)
    var = sqrSum/len(data_1d) -mean**2
    sorted_data = sorted(data_1d)
    median = sorted_data[len(data_1d)//2]
    return mean, var, median, min(data_1d), max(data_1d)

if __name__ == '__main__':
    data = read_data('data/class_score_en.csv')
    if data and len(data[0]) == 2:
        average = calc_weighted_average(data, [40/125, 60/100])

        with open('class_score_analysis.md', 'w') as report:
            report.write('### Individual Score\n\n')
            report.write('| Midterm | Final | Average |\n')
            report.write('| ------- | ----- | ----- |\n')
            for ((m_score, f_score), a_score) in zip(data, average):
                report.write(f'| {m_score} | {f_score} | {a_score:.3f} |\n')
            report.write('\n\n\n')

            report.write('### Examination Analysis\n')
            data_columns = {
                'Midterm': [m_score for m_score, _ in data],
                'Final'  : [f_score for _, f_score in data],
                'Average': average }
            for name, column in data_columns.items():
                mean, var, median, min_, max_ = analyze_data(column)
                report.write(f'* {name}\n')
                report.write(f'  * Mean: **{mean:.3f}**\n')
                report.write(f'  * Variance: {var:.3f}\n')
                report.write(f'  * Median: **{median:.3f}**\n')
                report.write(f'  * Min/Max: ({min_:.3f}, {max_:.3f})\n')