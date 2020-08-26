#include <cstdio>
#include <vector>
#include <algorithm>
#include <cassert>
using namespace std;

const int MAXN = 100000;
int n;
vector<int> v[MAXN];
vector<int> ans[MAXN];
bool visit[MAXN];

void init(){
    scanf("%d",&n);
    for(int i = 0 ; i < n ; i ++){
        v[i].clear();
        ans[i].clear();
    }
    fill(visit,visit+n,false);
    for(int i = 0 ; i < n ; i ++){
        int k;
        scanf("%d",&k);
        while(k--){
            int x;
            scanf("%d",&x);
            x--;
            v[i].push_back(x);
        }
    }
}

inline bool have_nei(int x,int k){
    return any_of(v[x].begin(),v[x].end(),[k](int nei){return nei==k;});
}

inline void add_edge(int x,int y){
    ans[x].push_back(y);
    ans[y].push_back(x);
    visit[x]=true;
    visit[y]=true;
}

void dfs(int now, int par){
    //printf("DFS now %d par %d\n",now+1,par+1);
    for(auto x:v[now])if(visit[x]==false){
        if(have_nei(x,par)){
            add_edge(x,now);
        }
    }
    for(auto x:ans[now])if(x!=par){
        dfs(x,now);
    }
}

void start2(int start){
    assert(v[v[start][0]].size() != v[v[start][1]].size());
    int next;
    if(v[v[start][0]].size() < v[v[start][1]].size()){
        next = v[start][0];
    }
    else{
        next = v[start][1];
    }
    add_edge(start,next);
    dfs(next,start);
}

// check x's neighbor should be a subset of a's U b's
bool check(int x, int a, int b){
    for(auto nei:v[x]){
        if((!have_nei(a,nei)) && (!have_nei(b,nei))){
            return false;
        }
    }
    return true;
}

void start3(int start){
    bool ok[3]{};
    int count = 0;
    if(v[v[start][0]].size()>3 && check(v[start][0],v[start][1],v[start][2])){
        ok[0]=true;
        count++;
    }
    if(v[v[start][1]].size()>3 && check(v[start][1],v[start][0],v[start][2])){
        ok[1]=true;
        count++;
    }
    if(v[v[start][2]].size()>3 && check(v[start][2],v[start][0],v[start][1])){
        ok[2]=true;
        count++;
    }
    int next=-1;
    if(count == 3){
        for(int i = 0 ; i < 3 ; i ++){
            if(v[v[start][i]].size()==7){
                assert(next==-1);
                next=v[start][i];
            }
        }
    }
    else if(count ==2){
        for(int i = 0 ; i < 3 ; i ++)if(ok[i]){
            if(v[v[start][i]].size()!=5){
                assert(next==-1);
                next=v[start][i];
            }
        }
    }
    else{
        for(int i = 0 ; i < 3 ; i ++)if(ok[i]){
            assert(next==-1);
            next=v[start][i];
        }
    }
    assert(next!=-1);
    add_edge(start,next);
    //printf("Using %d as start going to %d\n",start+1, next+1);
    dfs(next,start);
}

void solve(){
    for(int i = 0 ; i < n ; i ++){
        if(v[i].size()==2){
            start2(i);
            return;
        }
    }
    for(int i = 0 ; i < n ; i ++){
        if(v[i].size()==3){
            start3(i);
            return;
        }
    }
    assert(false);
}

void print(){
    for(int i = 0 ; i < n ; i ++){
        sort(ans[i].begin(),ans[i].end());
        printf("%d",(int)ans[i].size());
        for(auto x:ans[i]){
            printf(" %d",x+1);
        }
        puts("");
    }
}

int main (){
    int T;
    scanf("%d",&T);
    while(T--){
        init();
        solve();
        print();
    }
}

