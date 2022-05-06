public class Hashtable {
//Linked list and key-value for hashtable
    public static class List_Node {
        String key, value;
        List_Node next;
    }

    private List_Node[] table;

    private int count;

    public Hashtable() {
        table = new List_Node[100];
    }

    public Hashtable(int initialSize) {
        if (initialSize <= 0)
            throw new IllegalArgumentException("Size less than 0");
        table = new List_Node[initialSize];
    }


    public void insert(String key, String value) {

        assert key != null : "The key is null";

        int bucket = hash(key);

        List_Node list = table[bucket];
        while (list != null) {
            if (list.key.equals(key))
                break;
            list = list.next;
        }

        if (list != null) {
            list.value = value;
        }
        else {
            if (count >= 1.5*table.length) {
                resize();
                bucket = hash(key);
            }
            List_Node newNode = new List_Node();
            newNode.key = key;
            newNode.value = value;
            newNode.next = table[bucket];
            table[bucket] = newNode;
            count++;
        }
    }

    public String get(String key) {
        int bucket = hash(key);
        List_Node list = table[bucket];
        while (list != null) {
            if (list.key.equals(key))
                return list.value;
            list = list.next;
        }
        return null;
    }

    public void remove(String key) {
        int bucket = hash(key);
        if (table[bucket] == null) {
            return;
        }
        if (table[bucket].key.equals(key)) {
            table[bucket] = table[bucket].next;
            count--;
            return;
        }
        List_Node previous = table[bucket];
        List_Node current = previous.next;
        while (current != null && ! current.key.equals(key)) {
            current = current.next;
            previous = current;
        }
        if (current != null) {
            previous.next = current.next;
            count--;
        }
    }

    public boolean isKey(String key) {
        int bucket = hash(key);
        List_Node list = table[bucket];
        while (list != null) {
            if (list.key.equals(key))
                return true;
            list = list.next;
        }
        return false;
    }

    public int size() {
        return count;
    }

    private int hash(Object key) {
        return (Math.abs(key.hashCode())) % table.length;
    }

    private void resize() {
        List_Node[] newtable = new List_Node[table.length*2];
        for (int i = 0; i < table.length; i++) {
            List_Node list = table[i];
            while (list != null) {
                List_Node next = list.next;
                int hash = (Math.abs(list.key.hashCode())) % newtable.length;
                list.next = newtable[hash];
                newtable[hash] = list;
                list = next;
            }
        }
        table = newtable;
    }
}
