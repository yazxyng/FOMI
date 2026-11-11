import { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { checkServer } from '@/lib/api/health';

export default function HomeScreen() {
  const [status, setStatus] = useState('서버 연결을 확인해 주세요');
  const [loading, setLoading] = useState(false);
  async function check() {
    setLoading(true);
    try { await checkServer(); setStatus('서버 연결 성공'); }
    catch { setStatus('연결 실패: API 주소와 서버 실행 상태를 확인해 주세요'); }
    finally { setLoading(false); }
  }
  return (
    <SafeAreaView style={styles.page}>
      <View style={styles.content}>
        <Text style={styles.title}>FOMI</Text>
        <Text>작은 실천을 준비하는 공간</Text>
        <Pressable accessibilityRole="button" disabled={loading} onPress={check} style={styles.button}>
          <Text>{loading ? '확인 중…' : '개발 서버 연결 확인'}</Text>
        </Pressable>
        <Text accessibilityLiveRegion="polite">{status}</Text>
      </View>
    </SafeAreaView>
  );
}
const styles = StyleSheet.create({
  page: { flex: 1, backgroundColor: '#FFFFFF' },
  content: { flex: 1, justifyContent: 'center', padding: 24, gap: 20 },
  title: { fontSize: 36, fontWeight: '700', color: '#71655D' },
  button: { backgroundColor: '#E0D8CB', padding: 16, borderRadius: 16 },
});
