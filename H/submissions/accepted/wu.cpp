#include <bits/stdc++.h>
using namespace std;

const int INF = 1e9 + 7;
const int MAXN = 100005;

struct Node {
    int l, r;
    int ans;
    int la, lb;
    int ra, rb;
    bool operator==(const Node &rhs) const {
        return l == rhs.l && r == rhs.r && ans == rhs.ans && la == rhs.la &&
               lb == rhs.lb && ra == rhs.ra && rb == rhs.rb;
    }
};

Node Single{-1, -1, 1, 1, 1, 1, 1};
Node Bad{-1, -1, -INF, -INF, -INF, -INF, -INF};
Node seg[MAXN * 4];
int n, arr[MAXN];

inline int len(const Node &x) { return x.r - x.l + 1; }
inline int sign(const int &x) {
    if (x > 0) return 1;
    if (x < 0) return -1;
    assert(x != 0);
    return 0;
}

Node merge(const Node &a, const Node &b) {
    if (a == Bad) return b;
    if (b == Bad) return a;

    Node result{a.l, b.r, max(a.ans, b.ans), a.la, a.lb, b.ra, b.rb};

    // la
    if (a.la == len(a) &&
        sign(arr[a.l] - arr[a.l + 1]) == sign(arr[a.r] - arr[b.l])) {
        if ((len(b) == 1 ||
             sign(arr[a.l] - arr[a.l + 1]) == sign(arr[b.l] - arr[b.l + 1]))) {
            result.la = len(a) + b.la;
        }
        result.la = max(result.la, len(a) + 1);
    }
    // ra
    if (b.ra == len(b) &&
        sign(arr[b.r] - arr[b.r - 1]) == sign(arr[b.l] - arr[a.r])) {
        if ((len(a) == 1 ||
             sign(arr[b.r] - arr[b.r - 1]) == sign(arr[a.r] - arr[a.r - 1]))) {
            result.ra = len(b) + a.ra;
        }
        result.ra = max(result.ra, len(b) + 1);
    }
    // lb
    if (arr[a.l] > arr[a.l + 1]) result.lb = max(result.lb, result.la);
    if (arr[a.r] > arr[b.l] && a.lb == len(a)) {
        result.lb = max(result.lb, len(a) + 1);
        if (b.la > 1 && arr[b.l] > arr[b.l + 1]) {
            result.lb = max(result.lb, len(a) + b.la);
        }
    }
    if (arr[a.r] < arr[b.l] && a.la == len(a) &&
        (len(a) == 1 || arr[a.l] < arr[a.l + 1])) {
        result.lb = max(result.lb, len(a) + b.lb);
    }
    // rb
    if (arr[b.r] > arr[b.r - 1]) result.rb = max(result.rb, result.ra);
    if (arr[b.l] > arr[a.r] && b.rb == len(b)) {
        result.rb = max(result.rb, len(b) + 1);
        if (a.ra > 1 && arr[a.r] > arr[a.r - 1]) {
            result.rb = max(result.rb, len(b) + a.ra);
        }
    }
    if (arr[b.l] < arr[a.r] && b.ra == len(b) &&
        (len(b) == 1 || arr[b.r] < arr[b.r - 1])) {
        result.rb = max(result.rb, len(b) + a.rb);
    }
    // ans
    if (arr[a.r] > arr[b.l]) {
        result.ans = max(result.ans, a.rb + 1);
        if (b.la > 1 && arr[b.l] > arr[b.l + 1]) {
            result.ans = max(result.ans, a.rb + b.la);
        }
    } else if (arr[b.l] > arr[a.r]) {
        result.ans = max(result.ans, b.lb + 1);
        if (a.ra > 1 && arr[a.r] > arr[a.r - 1]) {
            result.ans = max(result.ans, b.lb + a.ra);
        }
    }

    result.ans = max({result.ans, result.la, result.lb, result.ra, result.rb});
    //    cout << "[" << result.l << ", " << result.r << "] " << result.ans << '
    //    '
    //         << result.la << ' ' << result.lb << ' ' << result.ra << ' '
    //         << result.rb << endl;
    return result;
}

void build(int o, int l, int r) {
    if (l == r) {
        cin >> arr[l];
        seg[o] = Single;
        seg[o].l = l;
        seg[o].r = r;
        return;
    }
    int mid = (l + r) / 2;
    build(o * 2, l, mid);
    build(o * 2 + 1, mid + 1, r);
    seg[o] = merge(seg[o * 2], seg[o * 2 + 1]);
}

void modify(int o, int l, int r, int POS, int VAL) {
    if (r < POS || POS < l) return;
    if (l == r) {
        arr[POS] = VAL;
        return;
    }
    int mid = (l + r) / 2;
    if (POS <= mid) {
        modify(o * 2, l, mid, POS, VAL);
    } else {
        modify(o * 2 + 1, mid + 1, r, POS, VAL);
    }
    seg[o] = merge(seg[o * 2], seg[o * 2 + 1]);
}

Node query(int o, int l, int r, int L, int R) {
    if (r < L || R < l) return Bad;
    if (L <= l && r <= R) {
        // cout << "[" << seg[o].l << ", " << seg[o].r << "] " << seg[o].ans <<
        // ' '
        //      << seg[o].la << ' ' << seg[o].lb << ' ' << seg[o].ra << ' '
        //      << seg[o].rb << endl;
        return seg[o];
    }
    int mid = (l + r) / 2;
    Node ql = query(o * 2, l, mid, L, R);
    Node qr = query(o * 2 + 1, mid + 1, r, L, R);
    return merge(ql, qr);
}

void init() {
    cin >> n;
    build(1, 0, n - 1);
}

void solve() {
    int Q;
    cin >> Q;
    int cmd, x, y;
    while (Q-- && cin >> cmd >> x >> y) {
        if (cmd == 1) {
            modify(1, 0, n - 1, x, arr[x] + y);
        } else {  // cmd == 2
            // cout << "query" << endl;
            // for (int i = 0; i < n; i++) cout << arr[i] << ' ';
            // cout << endl;
            cout << query(1, 0, n - 1, x, y).ans << '\n';
        }
    }
}

int main() {
    cin.tie(0);
    cin.sync_with_stdio(0);

    int T;
    cin >> T;

    while (T--) {
        init();
        solve();
    }
}
