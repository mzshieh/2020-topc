#include <cassert>
#include <iostream>
#include <random>
using namespace std;

const int T = 10;
const int MAXN = 100000;
const int MAXQ = 100000;

int main() {
    random_device rd;
    default_random_engine eng(rd());
    uniform_int_distribution<int> rg(0, MAXN-1), h(1,1'000'000'000);

    cout << T << '\n';
    for (int TT=T; TT--;) {
        vector<int> v;
        for (int i=1; i<=MAXN/2; i++)
            v.push_back(i);
        for (int i=MAXN/2; i>=1; i--)
            v.push_back(i);
        cout << MAXN << '\n';
        for (int i=0; i<MAXN; i++) {
            if (i) cout << ' ';
            cout << v[i];
        }
        cout << '\n';
        cout << MAXQ << '\n';
        for (int i=0; i<MAXQ; i++) {
            int op, x, y;
            if (i&1) {
                op = 2;
                x = rg(eng);
                while ( (y=rg(eng)) == x );
                if (x>y) swap(x, y);
            } else {
                op = 1;
                x = rg(eng);
                while (true) {
                    y = h(eng);
                    if (y == v[x]) continue;
                    if (x && v[x-1]==y) continue;
                    if (x==MAXN-1 && v[x+1]==y) continue;
                    break;
                }
                assert(1<=y&&y<=1000000000);
                y -= v[x];
                v[x] += y;
                assert(1<=v[x]&&v[x]<=1000000000);
            }
            cout << op << ' ' << x << ' ' << y << '\n';
        }
    }
}
