#include<bits/stdc++.h>

using namespace std;



void solve() {
    int n;
    scanf("%d",&n);
    vector<int> h(n);
    for(int i = 0; i < n; i++)
        scanf("%d",&h[i]);
    set<int> v;
    v.insert(n);
    for(int i = 1; i < n-1; i++)
        if (h[i]<h[i+1] and h[i]<h[i-1])
            v.insert(i);
    int q;
    scanf("%d",&q);
    while (q--) {
        int op, L, R;
        scanf("%d%d%d",&op,&L,&R);
        if (op==1) {
            h[L]+=R;
            for (int i = max(1,L-1); i < min(L+2,n-1); i++)
                if (h[i]<h[i+1] and h[i]<h[i-1]) {
                    v.insert(i);
                }
                else {
                    v.erase(i);
                }
        }
        else if(*v.lower_bound(L)>R) printf("%d\n",R-L+1);
        else {
            auto it = v.lower_bound(L);
            auto next_it = ++v.lower_bound(L);
            int ans = (*it)-L+1;
            for(; *next_it <= R; it++, next_it++) {
                //printf("%d,",*it);
                ans = max(ans, *next_it - *it +1);
            }
            ans = max(ans, R - *it + 1);
            printf("%d\n",ans);
        }
    }
}

int main() {
    int ncases;
    scanf("%d",&ncases);
    while(ncases--) solve();
    return 0;
}
