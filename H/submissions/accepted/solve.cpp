#include <cassert>
#include <algorithm>
#include <iostream>
using namespace std;

const int MAXN = 100000;
int a[MAXN], n;

struct Seg {
    int n, ans, ln, lv, rn, rv;
} seg[MAXN*4];

Seg merge(const Seg& l, const Seg& r) {
    Seg ret {
        l.n + r.n,
        max(l.ans, r.ans),
        l.ln, l.lv, r.rn, r.rv
    };
    if (r.lv<0 || l.rv>0) {
        ret.ans = max(ret.ans, r.ln+l.rn);
        if (l.ln==l.n)
            ret.ln = l.n+r.ln;
        if (r.rn==r.n)
            ret.rn = r.n+l.rn;
    }
    return ret;
}

void update(int p, int v, int L=1, int R=n, int x=1) {
    if (L==R) {
        seg[x].lv += v;
        seg[x].rv += v;
        return;
    }
    int M = (L+R)/2;
    if (p<=M) update(p, v, L, M, x*2);
    if (p>M)  update(p, v, M+1, R, x*2+1);
    seg[x] = merge(seg[x*2], seg[x*2+1]);
}

Seg query(int l, int r, int L=1, int R=n, int x=1) {
    if (l<=L&&R<=r) return seg[x];
    int M = (L+R)/2;
    if (r<=M) return query(l, r, L, M, x*2);
    if (l>M)  return query(l, r, M+1, R, x*2+1);
    return merge(query(l, r, L, M, x*2), query(l, r, M+1, R, x*2+1));
}

void build(int L=1, int R=n, int x=1) {
    if (L==R) {
        seg[x] = {1,1,1,a[L],1,a[L]};
    } else {
        int M = (L+R)/2;
        build(L, M, x*2);
        build(M+1, R, x*2+1);
        seg[x] = merge(seg[x*2], seg[x*2+1]);
    }
}

int main() {
    cin.sync_with_stdio(0); cin.tie(0);
    int T; cin >> T;
    assert(1<=T&&T<=10);
    while (T--) {
        cin >> n;
        assert(1<=n&&n<=100000);
        for (int i=0; i<n; i++) {
            cin >> a[i];
            assert(1<=a[i]&&a[i]<=1'000'000'000);
        }
        n --;
        for (int i=n; i>0; i--) a[i] -= a[i-1];
        build();
        for (int i=1; i<=n; i++) a[i] += a[i-1];
        int q; cin >> q;
        assert(1<=q&&q<=100000);
        while (q--) {
            int o, x, y; cin >> o >> x >> y;
            if (o==1) {
                a[x] += y;
                assert(1<=a[x]&&a[x]<=1'000'000'000);
                if (x)    update(x, y);
                if (x!=n) update(x+1, -y);
            } else {
                cout << query(x+1, y).ans+1 << '\n';
            }
        }
    }
}
