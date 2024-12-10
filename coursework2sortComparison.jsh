ArrayList<Integer> cardCompare(String card1, String card2) {
    int value1 = card1.charAt(0) == 'A' ? 1 : card1.charAt(0) - '0';
    int value2 = card2.charAt(0) == 'A' ? 1 : card2.charAt(0) - '0';
    String[] suits = {"H", "C", "D", "S"};
    int suit1Index = Arrays.asList(suits).indexOf(card1.charAt(1) + "");
    int suit2Index = Arrays.asList(suits).indexOf(card2.charAt(1) + "");

    if (suit1Index != suit2Index) {
        return suit1Index < suit2Index ? new ArrayList<>(Arrays.asList(-1)) : new ArrayList<>(Arrays.asList(1));
    } else {
        return value1 < value2 ? new ArrayList<>(Arrays.asList(-1)) : value1 > value2 ? new ArrayList<>(Arrays.asList(1)) : new ArrayList<>(Arrays.asList(0));
    }
}
ArrayList<String> bubbleSort(ArrayList<String> array) {
    for (int i = 0; i < array.size(); i++) {
        for (int j = 0; j < array.size() - i - 1; j++) {
            int comparison = cardCompare(array.get(j), array.get(j + 1)).get(0);
            if (comparison > 0) {
                Collections.swap(array, j, j + 1);
            }
        }
    }
    return array;
}
ArrayList<String> mergeSort(ArrayList<String> array) {
    if (array.size() <= 1) {
        return array;
    }
    ArrayList<String> left = new ArrayList<>(array.subList(0, array.size() / 2));
    ArrayList<String> right = new ArrayList<>(array.subList(array.size() / 2, array.size()));
    return merge(mergeSort(left), mergeSort(right));
}

private ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
    ArrayList<String> result = new ArrayList<>();
    while (!left.isEmpty() && !right.isEmpty()) {
        int comparison = cardCompare(left.get(0), right.get(0)).get(0);
        if (comparison <= 0) {
            result.add(left.remove(0));
        } else {
            result.add(right.remove(0));
        }
    }
    result.addAll(left);
    result.addAll(right);
    return result;
}
long measureBubbleSort(String filename) throws IOException {
    long startTime = System.nanoTime();
    ArrayList<String> cards = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = br.readLine()) != null) {
        cards.add(line.trim());
    }
    br.close();
    bubbleSort(cards);
    return (System.nanoTime() - startTime) / 1000000;
}
long measureMergeSort(String filename) throws IOException {
    long startTime = System.nanoTime();
    ArrayList<String> cards = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = br.readLine()) != null) {
        cards.add(line.trim());
    }
    br.close();
    mergeSort(cards);
    return (System.nanoTime() - startTime) / 1000000;
}
void sortComparison(String[] filenames) throws IOException {
    try (PrintWriter out = new PrintWriter("sortComparison.csv")) {
        out.println(", " + filenames[0].split("\\.")[0] + ", " + filenames[1].split("\\.")[0] + ", " + filenames[2].split("\\.")[0]);
        out.println("bubbleSort, " + measureBubbleSort(filenames[0]) + ", " + measureBubbleSort(filenames[1]) + ", " + measureBubbleSort(filenames[2]));
        out.println("mergeSort, " + measureMergeSort(filenames[0]) + ", " + measureMergeSort(filenames[1]) + ", " + measureMergeSort(filenames[2]));
    }
}
