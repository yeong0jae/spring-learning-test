package cholog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/members")
    public ResponseEntity<Member> create(@RequestBody Member member) {
        Member newMember = Member.toEntity(member, index.getAndIncrement());
        members.add(newMember);
        return ResponseEntity.created(URI.create("/members/" + newMember.getId())).body(member);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> read() {
        return ResponseEntity.ok(members);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody Member updatedMember) {
        Member member = members.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        member.update(updatedMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long memberId) {
        Member member = members.stream()
                .filter(it -> Objects.equals(it.getId(), memberId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        members.remove(member);

        return ResponseEntity.noContent().build();
    }
}
